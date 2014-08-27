#_
(:require [om-bootstrap.button :as b])

(b/split {:title "Dropdown right", :pull-right? true}
         (b/menu-item {:key 1} "Action")
         (b/menu-item {:key 2} "Another action")
         (b/menu-item {:key 3} "Something else here")
         (b/menu-item {:divider? true})
         (b/menu-item {:key 4} "Separated link"))
