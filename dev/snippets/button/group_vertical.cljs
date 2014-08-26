#_
(:require [om-bootstrap.button :as b])

(b/button-group
 {:vertical? true}
 (b/button {} "Button")
 (b/button {} "Button")
 (b/dropdown {:title "Dropdown"}
             (b/menu-item {:key 1} "Dropdown link")
             (b/menu-item {:key 2} "Dropdown link"))
 (b/button {} "Button")
 (b/button {} "Button")
 (b/dropdown {:title "Dropdown"}
             (b/menu-item {:key 1} "Dropdown link")
             (b/menu-item {:key 2} "Dropdown link"))
 (b/dropdown {:title "Dropdown"}
             (b/menu-item {:key 1} "Dropdown link")
             (b/menu-item {:key 2} "Dropdown link")))
