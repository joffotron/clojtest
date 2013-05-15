(ns clojtest.ciphers2)

(defn add-one-as-value
  "Example where we just add a key/value pair to an initial map, with the value hard-coded to 1
      (add-one-as-value {:a 2} :b) => {:b 1, :a 2}
      (add-one-as-value {\A 2} \B) => {\B 1, \A 2}"
  [initial-map key]
  (assoc initial-map key 1))

(defn incr-count
  "The (data-map key) part is the lookup/get e.g. ({\A 2} \A) returns the value 2.
  The zero on the end is default value if the key is not found"
  [data-map key] (inc (data-map key) 0))

(defn item-counts
  "So... combine the two concepts. Replace the hardcoded 1 with the incr-count'er
     (item-counts {:a 1, :b 2, :c 3} :b)  => {:a 1, :c 3, :b 3}"
  [initial-map key]
  (assoc initial-map key
    (incr-count initial-map key)))

(defn count-letters1
  "So reduce, starting with empty initial map, and feed in the text.
  (count-letters1 \"ABBBCD\")  => {\D 1, \C 1, \B 3, \A 1}"
  [text]
  (reduce
    (fn [char-counts char-key] (item-counts char-counts char-key))
    {}
    text))

;; Which can all be compacted down into:
(defn count-letters2 [text]
  (reduce
    (fn [counts character]
      (assoc counts character
        (inc (counts character 0))))
  {}
  text))



;(defn count-letters [text]
;  (reduce
;    (fn [counts character]
;      (update-in counts [character] (fnil inc 0)))
;    {}
;    text
;    )
;  )