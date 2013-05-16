(ns clojtest.guessing)

(defn guessing-game []
  (let [secret (rand-int 100)]
    (fn [guess]
      (cond
        (= guess secret) "You win!"
        (< guess secret) "Too low"
        :else "Too high"))))

