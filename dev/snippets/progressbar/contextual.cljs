#_
(:require [om-bootstrap.progress-bar :as pb])

(for [context [nil "primary" "success" "info" "warning" "danger"]]
  (pb/progress-bar (merge {:now 50 :label "50%"}
                          (when context {:bs-style context}))))