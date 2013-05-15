(ns clojtest.concurrent)

; *foo* is a convention for a dynamic var
(def ^:dynamic *foo* 5)

(defn print-foo [] (prn *foo*))

(print-foo)

;; bound value of *foo* gets passed all the way down the function call stack
;; avoid having to pass it as args to functions, but discouraged, as it creates state, as makes the fn less pure
(binding [*foo* 100]
  (print-foo))



(def fut (future (Thread/sleep 10000) 42))
(deref fut 1500 :timed-out)
