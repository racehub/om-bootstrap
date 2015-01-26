#_
(:require [om-bootstrap.progress-bar :as pb])

(pb/progress-bar {:min 0 :max 100 :now 50 :label "50%" :sr-only? true})