(ns clojtest.surgery)

(defn patient []
  (ref {:arms 2
         :legs 2
         :heads 1}))

(defn init-patients []
  (vec (repeatedly 10000 patient)))

(defn surgeon [specialty]
  (agent {:specialty specialty}))

(defn init-surgeons []
  (vec (take 100 (map surgeon (cycle [:arms :legs :heads])))))

(def all-patients nil)
(def all-surgeons nil)

(defn init! []
  (alter-var-root #'all-patients (constantly (init-patients)))
  (alter-var-root #'all-surgeons (constantly (init-surgeons))))

(defn totals []
  (dosync
    (reduce (partial merge-with +)
      (map deref all-patients))))

; -----

(defn transplant! [surgeon-state donor recipient]
  (let [part (:specialty surgeon-state)]
    (dosync
      (when (> (@donor part) 0)
        (alter donor update-in [part] dec)
        (alter recipient update-in [part] inc))))
  surgeon-state)

; ----

(defn select-patients
  "To select patients for transplant operations, our hospital randomly chooses two patients who are near each other."
  []
  (let [n1 (rand (count all-patients))
        n2 (mod (inc n1) (count all-patients))]
    [(nth all-patients n1) (nth all-patients n2)]))

(defn operate! []
  (let [surgn (rand-nth all-surgeons)
        [donor recipient] (select-patients)]
    (send surgn transplant! donor recipient)))

; ----

(defn run! []
  (init!)
  (dotimes [_ 20]
    (operate!)
    (println (totals)))
  )

; (take 100 (map deref all-patients))

; -------

; each room will do 1000 operations at some point in the future (in a thread)
(defn room []
  (future
    (dotimes [_ 1000] (operate!))))


(defn run-multi! []
  (init!)
  (let [rooms (doall (repeatedly 5 room))]
    (dorun (map deref rooms))
    (totals))
    )


(defn auditor []
  (future
    (dotimes [_ 50]
      (println (totals)))))


(defn run-multi2! []
  (init!)
  (println "Finding the variant")
  (let [ audit (auditor)
         rooms (doall (repeatedly 5 room))]
    (dorun (map deref rooms))
    (apply await all-surgeons)
    (deref audit)))



