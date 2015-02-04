#_
(:require [om-bootstrap.tab :as tab]
          [om-tools.dom :as d :include-macros true])

(t/tabbed-area {:active 2}
               {:key 1 :title "Home" :content "This is the home pane"}
               {:key 2 :title "Profile" :content "This is the profile pane"}
               {:key 3 :title "More" :content "This is the more pane"})