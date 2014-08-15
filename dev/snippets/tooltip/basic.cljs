#_
(:require [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(d/div {:style {:height 50}}
       (r/tooltip {:placement "right"
                   :position-left 150
                   :position-top 50}
                  (d/strong "Holy guacamole!")
                  "Check this info."))
