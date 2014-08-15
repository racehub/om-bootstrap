#_
(:require [om-bootstrap.button :as b]
          [om-tools.dom :include-macros true])

(d/div {:class "well"
        :style {:max-width 400
                :margin "0 auto 10px"}}
       (b/button {:bs-style "primary" :bs-size "large" :block? true}
                 "Block level button")
       (b/button {:bs-size "large" :block? true}
                 "Block level button"))
