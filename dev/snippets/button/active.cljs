#_
(:require [om-bootstrap.button :as b])

(b/toolbar {}
           (b/button {:bs-style "primary" :bs-size "large" :active? true}
                     "Primary button")
           (b/button {:bs-size "large" :bs-style "default" :active? true}
                     "Button"))
