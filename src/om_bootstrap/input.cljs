(ns om-bootstrap.input
  (:require [clojure.string :as string]
            [om.core :as om]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

;; ## Bootstrap Inputs for Om
;;
;; The following fields come from converting:
;;
;; https://github.com/react-bootstrap/react-bootstrap/blob/master/src/Input.jsx

;; React Bootstrap has some good stuff to learn from on how to make
;; inputs.
;;
;; https://github.com/react-bootstrap/react-bootstrap
;;
;;
;; QUESTION: Do we need the :key field here? :ref makes sense, not
;; sure about :key.
;;
;; QUESTION: Should have Input just take children as varargs, instead
;; of a specific key?

;; ### Schema

(def Addons
  {(s/optional-key :addon-before) (s/either s/Str t/Component)
   (s/optional-key :addon-after) (s/either s/Str t/Component)})

(def FeedbackIcons
  "Helps render feedback icons."
  {(s/optional-key :bs-style) (s/enum "success" "warning" "error")
   (s/optional-key :feedback?) s/Bool})

(def Input
  "Input fields that match these bad dawgs:
   https://github.com/react-bootstrap/react-bootstrap/blob/master/src/Input.jsx"
  (merge Addons
         FeedbackIcons
         {(s/optional-key :type) s/Str
          (s/optional-key :label) s/Str
          (s/optional-key :skip-form-group?) (s/named s/Bool "DON'T render a wrapping form group?")
          (s/optional-key :help) s/Str
          (s/optional-key :group-classname) s/Str
          (s/optional-key :wrapper-classname) s/Str
          (s/optional-key :label-classname) s/Str}))

(def Radio
  (t/bootstrap
   {:label s/Str
    (s/optional-key :checked?) s/Bool
    (s/optional-key :inline?) s/Bool}))

;; ### Utilities

(sm/defn class-set :- s/Str
  "Mimics the class-set behavior from React. Pass in a map of
  potential class to Boolean; you'll get back a class string that
  represents the final class to apply.

  TODO: Use class-set from om-tools."
  [klasses :- {(s/either s/Str s/Keyword) s/Bool}]
  (->> (mapcat (fn [[k keep?]]
                 (when keep? [(name k)]))
               klasses)
       (string/join " ")))

(sm/defn glyph :- t/Component
  "To be used with :addon-before or :addon-after."
  [glyph-name :- s/Str]
  (d/span {:class (str "glyphicon glyphicon-" glyph-name)}))

(sm/defn render-icon :- t/Component
  [{:keys [feedback? bs-style]} :- FeedbackIcons]
  (when feedback?
    (let [klasses {:glyphicon true
                   :form-control-feedback true
                   :glyphicon-ok (= "success" bs-style)
                   :glyphicon-warning-sign (= "warning" bs-style)
                   :glyphicon-remove (= "error" bs-style)}]
      (d/span {:class (class-set klasses)}))))

(sm/defn render-help
  [help :- (s/maybe s/Str)]
  (when help
    (d/span {:class "help-block"} help)))

(sm/defn render-input-group
  "Items is a vector of render instances."
  [{:keys [addon-before addon-after]} :- Addons
   items :- s/Any]
  (if (or addon-before addon-after)
    (d/div {:class "input-group"}
           (when addon-before
             (d/span {:class "input-group-addon"} addon-before))
           items
           (when addon-after
             (d/span {:class "input-group-addon"} addon-after)))
    items))

(sm/defn checkbox-or-radio? :- s/Bool
  "Returns true if the supplied input is of type checkbox or radio,
  false otherwise."
  [{type :type} :- Input]
  (or (= type "checkbox")
      (= type "radio")))

(sm/defn checkbox-or-radio-wrapper :- t/Component
  "Wraps this business in a div."
  [{type :type} :- Input
   children]
  (let [klasses {:checkbox (= "checkbox" type)
                 :radio (= "radio" type)}]
    (d/div {:class (class-set klasses)}
           children)))

(sm/defn render-label
  "This doesn't handle any control group stuff."
  ([input :- Input] (render-label input nil))
  ([{lc :label-classname label :label :as input} :- Input
    child]
     (let [classes (merge {:control-label (not (checkbox-or-radio? input))}
                          (when lc {lc (boolean lc)}))]
       (if label
         (d/label {:class (class-set classes)}
                  child
                  label)
         child))))

(sm/defn render-wrapper
  [{wc :wrapper-classname} :- Input
   child]
  (if wc
    (d/div {:class wc} child)
    child))

(sm/defn render-form-group :- t/Component
  "Wraps the entire form group."
  [{bs-style :bs-style cn :group-classname :as input} :- Input
   children]
  (let [classes (merge {:form-group (not (:skip-form-group? input))
                        :has-feedback (boolean (:feedback? input))
                        :has-success (= "success" bs-style)
                        :has-warning (= "warning" bs-style)
                        :has-error (= "error" bs-style)}
                       (when cn {cn (boolean cn)}))]
    (d/div {:class (class-set classes)}
           children)))

(sm/defn render-input :- t/Component
  [input :- Input attrs children]
  (let [props (fn [klass]
                (u/merge-props attrs {:class klass
                                      :ref "input"
                                      :key "input"}))]
    (if-not (:type input)
      children
      (case (:type input)
        "select" (d/select (props "form-control") children)
        "textarea" (d/textarea (props "form-control"))
        "static" (d/p (props "form-control-static") (:value attrs))
        (d/input (assoc (props (if (checkbox-or-radio? input)
                                 ""
                                 "form-control"))
                   :type (:type input))
                 children)))))

;; ### API Methods

(sm/defn input :- t/Component
  "Returns an input component. This currently does NOT handle any of
  the default values or validation messages that we'll need to make
  this work, though."
  [opts :- Input & children]
  (let [[input attrs] (t/separate Input opts)]
    (if (checkbox-or-radio? input)
      (->> [(->> (render-input input attrs children)
                 (render-label input)
                 (checkbox-or-radio-wrapper input))
            (render-help (:help input))]
           (render-wrapper input)
           (render-form-group input))
      (->> [(render-label input)
            (->> [(render-input-group
                   (select-keys input [:addon-before :addon-after])
                   (render-input input attrs children))
                  (render-icon (select-keys input [:feedback? :bs-style]))
                  (render-help (:help input))]
                 (render-wrapper input))]
           (render-form-group input)))))

;; ### Input Candidates
;;
;; These bad dawgs need to be abstracted out into more solid input
;; components. Putting them here for now.

(sm/defn radio-option :- t/Component
  "Generates a radio button entry, to place into a radio button
   grouping."
  [opts :- Radio]
  (let [[bs props] (t/separate Radio opts {:ref "input"
                                           :key "input"
                                           :type "radio"})
        {:keys [label checked? inline?]} bs
        core (d/input (assoc props :checked checked?))]
    (if inline?
      (d/label {:class "radio-inline"} core label)
      (d/div {:class "radio"} (d/label {} core label)))))

(sm/defn options :- [t/Component]
  "Returns a sequence of options for use as the children of a select
  input."
  [header :- s/Str
   opts :- [(s/pair s/Str "option value"
                    s/Str "option label")]]
  (cons (d/option {:value ""} header)
        (for [[v label] opts]
          (d/option {:value v} label))))
