#_
(:require [om-bootstrap.modal :as md])

(md/modal {:header (d/h4 "This is a Modal")
           :footer (b/button {} "Save")
           :close-button? true
           :visible? true}
          "This should be in the Modal body")