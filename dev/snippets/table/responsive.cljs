#_
(:require [om-bootstrap.table :refer [table]]
          [om-tools.dom :as d :include-macros true])

(table {:responsive? true}
       (d/thead
        (d/tr
         (d/th "#")
         (repeat 6 (d/th "Table heading")))
        (d/tbody
         (for [i (range 3)]
           (d/tr
            (d/td (str (inc i)))
            (repeat 6 (d/td "Table cell")))))))
