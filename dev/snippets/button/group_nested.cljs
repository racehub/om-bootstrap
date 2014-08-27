#_
(:require [om-bootstrap.button :as b])

(b/button-group
 {}
 (b/button {} "1")
 (b/button {} "2")
 (b/dropdown {:title "Dropdown"}
             (b/menu-item {:key 1} "Dropdown link")
             (b/menu-item {:key 2} "Dropdown link")))
