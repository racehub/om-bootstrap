#_
(:require [om-bootstrap.button :as b]
          [om-bootstrap.nav :as n]
          [om-tools.dom :as d :include-macros true])

(n/navbar
 {:brand (d/a {:href "#"}
              "Navbar")}
 (n/nav
  {:collapsible? true}
  (n/nav-item {:key 1 :href "#"} "Link")
  (n/nav-item {:key 2 :href "#"} "Link")
  (b/dropdown {:key 3, :title "Dropdown"}
              (b/menu-item {:key 1} "Action")
              (b/menu-item {:key 2} "Another action")
              (b/menu-item {:key 3} "Something else here")
              (b/menu-item {:divider? true})
              (b/menu-item {:key 4} "Separated link"))
  :right
  (n/nav-item {:key 1 :href "#"} "Right")))
