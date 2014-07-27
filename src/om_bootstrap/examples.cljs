(ns om-bootstrap.examples
  (:require [om-bootstrap.button :as b]
            [om-bootstrap.grid :as g]
            [om-bootstrap.input :as i]
            [om-bootstrap.random :as r]))

;; ## Button

(comment
  "Examples:"
  (b/toolbar {} (b/button {} "Default")
             (b/button {:bs-style "primary"} "Primary")
             (b/button {:bs-style "success"} "Success")
             (b/button {:bs-style "info"} "Info")
             (b/button {:bs-style "warning"} "Warning")
             (b/button {:bs-style "danger"} "Danger")
             (b/button {:bs-style "link"} "Link"))

  "Button sizing: (TODO: Finish converting the example!)"
  (d/div {}
         (b/toolbar {}
                    (b/button {:bs-style "primary" :bs-size "large"} "Large button")
                    (b/button {:bs-size "large"} "Large button"))
         (b/toolbar {}
                    (b/button {:bs-style "primary" :bs-size "large"} "Default button")
                    (b/button {:bs-size "large"} "Large button"))
         (b/toolbar {}
                    (b/button {:bs-style "primary" :bs-size "large"} "Small button")
                    (b/button {:bs-size "large"} "Large button"))
         (b/toolbar {}
                    (b/button {:bs-style "primary" :bs-size "xsmall"}
                              "Extra small button")
                    (b/button {:bs-size "large"} "Large button"))))

;; ## Jumbotron

(comment
  (r/jumbotron {}
               (d/h1 {} "Hello, World!")
               (d/p "This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.")
               (d/p {} (b/button {:bs-style "primary"} "Learn More"))))

;; ## Label

(comment
  "default:"
  (d/div {}
         (d/h1 {} "Label " (r/label {} "New"))
         (d/h2 {} "Label " (r/label {} "New"))
         (d/h3 {} "Label " (r/label {} "New"))
         (d/h4 {} "Label " (r/label {} "New"))
         (d/h5 {} "Label " (r/label {} "New"))
         (d/p {} "Label " (r/label {} "New")))

  "Variations:"
  (d/div {}
         (r/label {:bs-style "default"} "Default")
         (r/label {:bs-style "primary"} "Primary")
         (r/label {:bs-style "success"} "Success")
         (r/label {:bs-style "info"} "Info")
         (r/label {:bs-style "warning"} "Warning")
         (r/label {:bs-style "danger"} "Danger")))

;; ## Well

(comment
  (def default-well
    (r/well {} "Look, I'm in a well!"))

  (def optional-classes
    (d/div {}
           (r/well {:bs-size "large"} "Look, I'm in a large well!")
           (r/well {:bs-size "small"} "Look, I'm in a small well!"))))

;; ## Page Header

(comment
  (r/page-header {} "Example page header " (d/small {} "Subtext for header")))

;; ## Grid

(comment
  (g/grid {}
          (g/row {:class "show-grid"}
                 (g/col {:xs 12 :md 8}
                        (d/code {} "(g/col {:xs 12 :md 8})"))
                 (g/col {:xs 6 :md 4}
                        (d/code {} "(g/col {:xs 6 :md 4})")))
          (g/row {:class "show-grid"}
                 (g/col {:xs 6 :md 4}
                        (d/code {} "(g/col {:xs 6 :md 4})"))
                 (g/col {:xs 6 :md 4}
                        (d/code {} "(g/col {:xs 6 :md 4})"))
                 (g/col {:xs 6 :md 4}
                        (d/code {} "(g/col {:xs 6 :md 4})")))
          (g/row {:class "show-grid"}
                 (g/col {:xs 6 :xs-offset 6}
                        (d/code {} "(g/col {:xs 6 :xs-offset 6})")))
          (g/row {:class "show-grid"}
                 (g/col {:md 6 :md-push 6}
                        (d/code {} "(g/col {:md 6 :md-push 6})"))
                 (g/col {:md 6 :md-pull 6}
                        (d/code {} "(g/col {:md 6 :md-push 6})")))))

;; ## Tooltip

(comment
  "Tooltip component:"
  (d/div {:style {:height 50}}
         (t/tooltip {:placement "right"
                     :position-left 150
                     :position-top 50}
                    (d/strong "Holy guacamole!")
                    "Check this info."))

  "Positioned tooltip component. (TODO: Needs overlay trigger to finish!)"
  (let [tooltip  (r/tooltip {}
                            (d/strong "Holy guacamole!")
                            "Check this info.")]
    (b/toolbar {}
               (overlay-trigger {:placement "left" :overlay tooltip})
               (overlay-trigger {:placement "top" :overlay tooltip})
               (overlay-trigger {:placement "bottom" :overlay tooltip})
               (overlay-trigger {:placement "right" :overlay tooltip})))

  "Positioned tooltip in copy. (TODO: Needs overlay trigger to finish!)"
  (defn link-with-tooltip
    [{:keys [tooltip href]} & children]
    (overlay-trigger {:placement "top"
                      :overlay (r/tooltip {} tooltip)
                      :delay-show 300
                      :delay-hide 150}
                     (d/a {:href href} children)))

  (d/p {:class "muted" :style {:margin-bottom 0}}
       "Call me Ishmael. Some years ago - never mind how long "
       (link-with-tooltip {:tooltip "Probably about two." :href "#"} "precisely")
       " - having little or no money in my purse, and nothing particular to interest me on shore, I thought I would sail about a little and see the watery part of the world. It is a way I have of driving off the spleen and regulating the circulation. Whenever I find myself growing grim about the mouth; whenever it is a damp, drizzly "
       (link-with-tooltip {:tooltip "The eleventh month!" :href "#"} "November")
       " in my soul; whenever I find myself involuntarily pausing before coffin warehouses, and bringing up the rear of every funeral I meet; and especially whenever my hypos get such an upper hand of me, that it requires a strong moral principle to prevent me from deliberately stepping into the "
       (link-with-tooltip {:tooltip "A large alley or a small avenue." :href "#"} "street")
       "m and methodically knocking people's hats off - then, I account it high time to get to sea as soon as I can. This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself upon his sword; I quietly take to the ship. There is nothing surprising in "
       (link-with-tooltip {:tooltip "The ship, that is." :href "#"} "this")
       ". If they but knew it, almost all men in their degree, some time or other, cherish very nearly the same feelings towards the ocean with me."))

;; ## Alerts

(comment
  "Alert component."

  "Basic alert styles:"
  (r/alert {:bs-style "warning"}
           (d/strong "Holy guacamole!")
           "Best check yo self, you're not looking too good.")

  "Closeable alerts, just pass in a onDismiss function."
  ;; Fill in.

  "Auto close after a set time with dismissAfter prop."
  ;; Fill in.

  )
