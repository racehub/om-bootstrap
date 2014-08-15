(ns om-bootstrap.macros
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defmacro slurp-example
  "Takes a snippet name and returns a map with the code string under
  `:code`, and the evaluated body under `:body`."
  [snippet]
  (let [s (slurp (io/resource (format "snippets/%s.cljs" snippet)))]
    `(do {:code ~(s/replace s "#_\n" "")
          :body ~(read-string (str "(do " s ")"))})))
