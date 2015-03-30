#_
(:require [om-bootstrap.pagination :as pg])

(pg/pagination {}
  (pg/page {} "1")
  (pg/page {} "2")
  (pg/page {} "3"))
