#_
(:require [om-bootstrap.button :as b]
          [om-tools.dom :as d :include-macros true])

(d/div
 (for [[size title] [["large" "Large button"]
                     ["small" "Small button"]
                     ["xsmall" "Extra small button"]]
       :let [style (.toLowerCase title)]]
   (b/toolbar
    {}
    (b/dropdown {:bs-size size, :title title}
                (b/menu-item {:key 1} "Action")
                (b/menu-item {:key 2} "Another action")
                (b/menu-item {:key 3} "Something else here")
                (b/menu-item {:divider? true})
                (b/menu-item {:key 4} "Separated link")))))
