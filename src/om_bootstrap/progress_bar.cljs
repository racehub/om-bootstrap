(ns om-bootstrap.progress-bar
  "IN PROGRESS! (Yuk yuk.) This code is a start, but currently doesn't
  work."
  (:require [om-bootstrap.types :as t]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s :include-macros true]
            [om-bootstrap.util :as u]))

;; ## Schema

(def ProgressBar
  (t/bootstrap
   {(s/optional-key :min) s/Int
    :now s/Int
    (s/optional-key :max) s/Int
    (s/optional-key :label) t/Renderable
    (s/optional-key :sr-only?) (s/named s/Bool "Screenreader-only? Hide the label?")
    (s/optional-key :striped?) s/Bool
    (s/optional-key :active?) s/Bool}))

(def defaults
  {:min 0
   :max 100
   :bs-class "progress-bar"
   :striped? false
   :active? false})

(s/defn percentage :- s/Num
  [min :- s/Int now :- s/Int max :- s/Int]
  (-> (/ (- now min)
         (- max min))
      (* 100)
      (Math/ceil)))

(s/defn progress-bar :- t/Component
  "Generates a Bootstrap progress bar component."
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
        style {:width  (str (percentage (:min bs) (:now bs) (:max bs)) "%")}]
    (d/div {:class "progress"}
           (d/div (u/merge-props props
                                 {:class (d/class-set classes)}
                                 values
                                 {:style style})
                  (when-let [label (:label bs)]
                    (if (:sr-only? bs)
                      (d/span {:class "sr-only"} label)
                      label)))
           children)))
