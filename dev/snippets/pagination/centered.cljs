#_
(:require [om-bootstrap.pagination :as pg])

(pg/pagination {:class "text-center"}
               (pg/page {} "1")
               (pg/page {} "2")
               (pg/page {} "3"))
