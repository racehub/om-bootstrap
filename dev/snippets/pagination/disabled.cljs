#_
(:require [om-bootstrap.pager :as pg])

(pg/pagination {}
  (pg/previous {:disabled? true})
  (pg/page {} "1")
  (pg/page {} "2")
  (pg/page {} "3")
  (pg/next {}))