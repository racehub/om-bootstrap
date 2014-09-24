(ns om-bootstrap.progress-bar
  "IN PROGRESS! (Yuk yuk.) This code is a start, but currently doesn't
  work."
  (:require [om-bootstrap.types :as t]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s :include-macros true]))

;; ## Schema

(def ProgressBar
  (t/bootstrap
   {:min s/Int
    :now s/Int
    :max s/Int
    :label t/Renderable
    :sr-only? (s/named s/Bool "Screenreader-only? Hide the label?")
    :striped? s/Bool
    :active? s/Bool}))

(def defaults
  {:min 0 :max 100 :bs-class "progress-bar"})

(s/defn percentage :- s/Num
  [min :- s/Int now :- s/Int max :- s/Int]
  (-> (/ (- now min)
         (- max min))
      (* 100)
      (Math/ceil)))

(s/defn sr-only-label :- t/Component
  "Renders a screenreader-only label."
  [label :- t/Renderable]
  (d/span {:class "sr-only"} label))

(comment
  (s/defn render-label
    "There's some bullshit in here able interpolation that I have to
    figure out."
    [percentage :- s/Num]

    )

  "Actual progress bar rendering."
  (s/defn render-progress-bar
    [{:keys [label]} :- ProgressBar]
    (cond
     (string? label) (render-label label)
     (nil?)
     )))

(s/defn progress-bar :- t/Component
  "Generates a Bootstrap progress bar component."
  [opts :- ProgressBar & children]
  (let [[bs props] (t/separate ProgressBar opts defaults)
        classes (-> (cond
                     (:active? bs) {:progress-striped true
                                    :active true}
                     (:striped? bs) {:progress-striped true}
                     :else {})
                    (assoc :progress true))
        ]
    ))
