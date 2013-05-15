(ns student.dialect
  (:require [clojure.string :as str]))

(defn canada [sentence]
  (str/replace sentence #"\.$" ", eh?"))






