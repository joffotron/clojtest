(ns clojtest.macros
  (:require [clojure.pprint :refer (pprint)]))

(defmacro just-do-it
  "takes a body of expressions and expands to code which evaluates the expressions in order"
  [& body]
  `(do ~@body))

(defmacro execute
  "takes one expression and expands to code which first prints the expression, then evaluates it and prints the result."
  [expr]
  `(do
    (pprint '~expr)
    (try
      (pprint ~expr)
     (catch Throwable e#
       (clojure.repl/pst e#)))
     ))


