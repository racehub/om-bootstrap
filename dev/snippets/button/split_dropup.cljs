#_
(:require [om-bootstrap.button :as b]
          [om-tools.dom :as d :include-macros true])

(d/div
 (b/toolbar
  {}
  (b/split {:title "Dropup", :dropup? true}
           (b/menu-item {:key 1} "Action")
           (b/menu-item {:key 2} "Another action")
           (b/menu-item {:key 3} "Something else here")
           (b/menu-item {:divider? true})
           (b/menu-item {:key 4} "Separated link")))
 (b/toolbar
  {}
  (b/split {:title "Right dropup"
            :bs-style "primary"
            :dropup? true
            :pull-right? true}
           (b/menu-item {:key 1} "Action")
           (b/menu-item {:key 2} "Another action")
           (b/menu-item {:key 3} "Something else here")
           (b/menu-item {:divider? true})
           (b/menu-item {:key 4} "Separated link"))))
