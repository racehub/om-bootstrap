#_
(:require [om-bootstrap.progress-bar :as pb])

(pb/progress-bar {}
                 (pb/progress-bar {:now 35 :bs-style "success" :nested? true})
                 (pb/progress-bar {:now 20 :bs-style "warning" :striped? true :nested? true})
                 (pb/progress-bar {:now 10 :bs-style "danger" :nested? true}))
