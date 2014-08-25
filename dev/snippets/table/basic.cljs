#_
(:require [om-bootstrap.table :refer [table]]
          [om-tools.dom :as d :include-macros true])

(table {:striped? true :bordered? true :condensed? true :hover? true}
       (d/thead
        (d/tr
         (d/th "#")
         (d/th "First Name")
         (d/th "Last Name")
         (d/th "Username")))
       (d/tbody
        (d/tr
         (d/td "1")
         (d/td "Mark")
         (d/td "Otto")
         (d/td "@mdo"))
        (d/tr
         (d/td "2")
         (d/td "Jacob")
         (d/td "Thornton")
         (d/td "@fat"))
        (d/tr
         (d/td "3")
         (d/td {:col-span 2} "Larry the Bird")
         (d/td "@twitter"))))
