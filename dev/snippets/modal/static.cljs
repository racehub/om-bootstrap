#_
(:require [om-bootstrap.modal :as md])

(md/modal {:header "This is a Modal"
           :footer "Are you done?"
           :close-button? true}
          "This should be in the Modal body")