#_
(:require [om-bootstrap.button :as b])

(b/button-group
 {:justified? true}
 (b/button {:href "#"} "Left")
 (b/button {:href "#"} "Middle")
 (b/dropdown {:title "Dropdown"}
             (b/menu-item {:key 1} "Dropdown link")
             (b/menu-item {:key 2} "Dropdown link")))
