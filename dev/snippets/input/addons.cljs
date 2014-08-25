#_
(:require [om-bootstrap.input :as i]
          [om-tools.dom :as d :include-macros true])

(d/form
 (i/input {:type "text" :addon-before "@"})
 (i/input {:type "text" :addon-after ".00"})
 (i/input {:type "text" :addon-before "$" :addon-after ".00"}))
