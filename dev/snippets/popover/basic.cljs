#_
(:require [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(d/div {:style {:height 120}}
       (r/popover {:placement "right"
                   :position-left 200
                   :position-top 50
                   :title "Popover right"}
                  "And here's some "
                  (d/strong "amazing")
                  " content. It's very engaging. Right?"))
