(ns clojtest.ciphers)

(def fibs
  (map first
    (iterate (fn [[a b]] [b (+ a b)])
      [0 1])))

; (reduce + (take 50 ciphers/fibs))

(def primes
  (letfn [(next-prime [known-primes n]
            (lazy-seq
              (if (some #(zero? (rem n %)) known-primes)
                (next-prime known-primes (inc n))
                (cons n (next-prime (conj known-primes n) (inc n))))))]
    (next-prime [] 2)))

; (reduce + (take 50 (filter #(> % 100) ciphers/primes)))

(def letters (map char
               (range
                 (int \A)
                 (inc (int \Z)))))

; (take 26 (drop 13 (cycle letters)))

(defn rotx [n collection]
  (take (count collection)
    (drop n (cycle collection))))

(defn rot13-collection [collection] (rotx 13 collection))

(def pairs (map vector letters (rot13-collection letters)))

; Pour into a map
; (into {} pairs)

(def rot13-cipher (zipmap letters (rot13-collection letters)))

(defn rot13-one-char [c] (get rot13-cipher c c) )

; (apply str [\A \B \C]) => "ABC"

(defn rot13 [ciphertext]
  (apply str (map rot13-one-char (seq ciphertext))))

(def secret-message
  "FCMJ C CM U JLIALUGGCHA MSMNYG ZIL NBY CVG 704 ZIL WIGJONCHA QCNB
MSGVIFCW YRJLYMMCIHM. CN BUM VYYH OMYX ZIL MSGVIFCW WUFWOFUNCIHM CH
XCZZYLYHNCUF UHX CHNYALUF WUFWOFOM, YFYWNLCW WCLWOCN NBYILS,
GUNBYGUNCWUF FIACW, UHX ULNCZCWCUF CHNYFFCAYHWY.")



;(defn count-letters [text]
;  (reduce
;    (fn [counts character]
;      (assoc counts character
;        (inc (counts character 0))))
;  {}
;  text))

(defn count-letters [text]
  (reduce
    (fn [counts character]
      (update-in counts [character] (fnil inc 0)))
    {}
    text
    )
  )

; (sort-by (count-letters secret-message) letters)

(- (int \I) (int \C))

(defn rot6-collection [collection] (rotx 6 letters))

; Mapping for Rot 6
(def rot6-cipher (zipmap letters (rot6-collection letters)))

;function to get at the mapping
(defn rot6-one-char [c] (get rot6-cipher c c) )

(defn rot6 [ciphertext]
  (apply str (map rot6-one-char (seq ciphertext))))

(println (rot6 secret-message))
