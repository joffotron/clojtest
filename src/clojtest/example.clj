(ns clojtest.example)

(defn hello []
  (println "Hello, World!"))


(merge {:a 5, :b 10} {:b 20 :c 30})

(merge-with + {:a 5, :b 10} {:b 20 :c 30})

(def merge-by-adding (partial merge-with +))

(merge-by-adding {:a 5, :b 10} {:b 20 :c 30})



