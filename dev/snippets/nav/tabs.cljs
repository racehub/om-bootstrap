#_
(:require [om-bootstrap.nav :as n])

(n/nav {:bs-style "tabs"
        :active-key 1
        :on-select (fn [k _] (js/alert (str "Selected " k)))}
       (n/nav-item {:key 1 :href "/home"}
                   "nav-item 1 content")
       (n/nav-item {:key 2 :href "/home"}
                   "nav-item 2 content")
       (n/nav-item {:key 3 :href "/home" :disabled? true}
                   "nav-item 3 content"))
