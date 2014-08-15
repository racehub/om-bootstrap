#_
(:require [om-bootstrap.button :as b])

(b/toolbar {}
           (b/button {} "Default")
           (b/button {:bs-style "primary"} "Primary")
           (b/button {:bs-style "success"} "Success")
           (b/button {:bs-style "info"} "Info")
           (b/button {:bs-style "warning"} "Warning")
           (b/button {:bs-style "danger"} "Danger")
           (b/button {:bs-style "link"} "Link"))
