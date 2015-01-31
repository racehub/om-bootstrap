(ns om-bootstrap.progress-bar
  "IN PROGRESS! (Yuk yuk.) This code is a start, but currently doesn't
  work."
  (:require [om-bootstrap.types :as t]
            [om-tools.dom :as d :include-macros true]
            [om-bootstrap.util :as u]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

;; ## Schema

(def ProgressBar
  (t/bootstrap
   {:now s/Int
    (s/optional-key :min) s/Int
    (s/optional-key :max) s/Int
    (s/optional-key :label) t/Renderable
    (s/optional-key :sr-only?) (s/named s/Bool "Screenreader-only? Hide the label?")
    (s/optional-key :striped?) s/Bool
    (s/optional-key :active?) s/Bool
    (s/optional-key :nested?) (s/named s/Bool "Specify this for a nested ProgressBar inside a stacked ProgressBar.")}))

(def defaults
  {:min 0
   :max 100
   :bs-class "progress-bar"
   :striped? false
   :active? false
   :nested? false})

(sm/defn percentage :- s/Num
  [min :- s/Int now :- s/Int max :- s/Int]
  (-> (/ (- now min)
         (- max min))
      (* 100)
      (Math/ceil)))

(sm/defn child-bar :- t/Component
  "Generates a progress bar child."
  [opts :- ProgressBar & children]
  (let [[bs props] (t/separate ProgressBar opts defaults)
        classes (merge
                 (t/bs-class-set bs)
                 {:progress-bar true}
                 (when (:active? bs) {:progress-bar-striped true
                                      :active true})
                 (when (:striped? bs) {:progress-bar-striped true}))
        values {:aria-value-min (:min bs)
                :aria-value-max (:max bs)
                :aria-value-now (:now bs)}
        style {:width (str (percentage (:min bs) (:now bs) (:max bs)) "%")}]
    (d/div (u/merge-props props
                          {:class (d/class-set classes)}
                          values
                          {:style style})
           (when-let [label (:label bs)]
             (if (:sr-only? bs)
               (d/span {:class "sr-only"} label)
               label)))))

(sm/defn progress-bar
  "Generates a progress bar component."
  [opts :- ProgressBar & children]
  (if (:nested? opts)
    (child-bar opts children)
    (d/div {:class "progress"}
           (child-bar opts children)
           children)))
