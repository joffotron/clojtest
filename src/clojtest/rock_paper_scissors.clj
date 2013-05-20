(ns clojtest.rock-paper-scissors)

(def dominates {:rock :scissors,
                :scissors :paper,
                :paper :rock})

(def choices (vec (keys dominates)))

(defn winner
  "Determine the winner"
  [p1-choice p2-choice]
  (cond
    (= p1-choice p2-choice) nil
    (= (p1-choice dominates) p2-choice) p1-choice
    :default p2-choice
    ))

(defn draw? [p1-choice p2-choice] (= p1-choice p2-choice))

(defn iwon? [me you] (= (winner me you) me))

(defprotocol Player
  (choose [player])
  (update-player [p my-choice your-choice])
  )

(defrecord RandomPlayer []
  Player
  (choose [player] (rand-nth choices))
  (update-player [self _ _] self)
  )

(defrecord StubbornPlayer [choice]
  Player
  (choose [_] choice)
  (update-player [this _ _] this))

(defrecord MeanPlayer [last-winner]
  Player
  (choose [_]
    (if last-winner last-winner (rand-nth choices)))
  (update-player [_ me you]
    (->MeanPlayer (when (iwon? me you) me))))


; {player1 score, player2 score}
;iwon? (choose (p1) (choose p2))

(defn reduct-game [scores round]
  (let [ p1 (first (keys scores))
         p2 (second (keys scores))
         match-choices [(choose p1) (choose p2)] ]
;    (prn match-choices)
    (update-player p1 (first match-choices) (second match-choices))
    (update-player p2 (second match-choices) (first match-choices))
    (if (apply draw? match-choices)
      scores
      (if (apply iwon? match-choices)
          (update-in scores [p1] inc)   ; increment score for me, update player
          (update-in scores [p2] inc)))))      ; increment score for them, update player

(defn game [player1 player2 rounds]
  (reduce
    reduct-game
    {player1 0, player2 0} ; initial map
      (range 0 rounds) ; sequence to loop
    ))

(defn show-result [game-result]
  (let [p1-score (first (vals game-result))
         p2-score (second (vals game-result))]
     (println "Player 1: " p1-score)
     (println "Player 2: " p2-score)))

