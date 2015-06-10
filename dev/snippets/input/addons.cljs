#_
(:require [om-bootstrap.input :as i]
          [om-bootstrap.button :as b]
          [om-tools.dom :as d :include-macros true])

(d/form
 (i/input {:type "text" :addon-before "@"})
 (i/input {:type "text" :addon-after ".00"})
 (i/input {:type "text" :addon-before "$" :addon-after ".00"})
 (i/input {:type "text" :addon-button
                 (b/button {:bs-style "primary" :onClick #(js/alert "hi!")} "Click Me!")}))
