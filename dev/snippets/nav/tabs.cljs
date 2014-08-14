#_
(:require [om-bootstrap.nav :as n])

(let [on-select (fn [k _] (js/alert (str "Selected " k)))]
  (n/nav {:bs-style "tabs"}
         (n/nav-item {:key 1 :href "/home" :active? true
                      :on-select on-select}
                     "nav-item 1 content")
         (n/nav-item {:key 2 :href "/home"
                      :on-select on-select}
                     "nav-item 2 content")
         (n/nav-item {:key 3 :href "/home" :disabled? true
                      :on-select on-select}
                     "nav-item 3 content")))
