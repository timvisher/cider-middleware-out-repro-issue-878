# CIDER '`middleware.out` may stop working after a reconnect?' Repro

[cidre-nrepl/Issue 878](https://github.com/clojure-emacs/cider-nrepl/issues/878)

Note this may have something to do with incomplete Log4j support noted [here in the manual](https://docs.cider.mx/cider/debugging/logging.html#log-framework). That links to [clojure-emacs/logjam issue #2](https://github.com/clojure-emacs/logjam/issues/2) which talks about dynamic reconfiguration of the appender being wiped out somehow by log4j2's reconfiguration mechanism. So this could be a known issue.

It's possible to reproduce this with both logback and log4j2, though, so ↑ may be incorrect since logback is supposed to be fully supported based on the manual.

1. Run CIDER (`bin/start-cider`)

    ```
    $ bin/start-cider-logback # or -log4j2
    2024-06-05T13:40:46+0000
    openjdk version "21.0.2" 2024-01-16
    OpenJDK Runtime Environment Homebrew (build 21.0.2)
    OpenJDK 64-Bit Server VM Homebrew (build 21.0.2, mixed mode, sharing)
    Clojure CLI version 1.11.3.1463
    nREPL server started on port 60294 on host localhost - nrepl://localhost:60294    ```
    ```

1. Connect from Emacs

    ```
    $ bin/start-emacs cider-middleware-out-repro-issue-878.clj
    ```
    
    The messages buffer will contain information about CIDER and Emacs versions.

    ```
    ;; e.g. on 2024-06-05T13:36:06+0000
    CIDER 1.14.0 (Verona)
    GNU Emacs 29.3 (build 1, aarch64-apple-darwin23.4.0)
     of 2024-03-24
    ```

    ```
    M-x cider-connect RET localhost RET <port from ↑ e.g. 60294> RET
    ```

1. Eval `(log-forever)`

    ```
    M-x cider-switch-to-last-clojure-buffer RET
    ```

    ```
    M-x cider-load-buffer RET
    ```

    Navigate to the comment block and eval `log-forever`

    ```
    M-x cider-eval-last-sexp RET
    ```

1. Observe some logs in the repl buffer

1. Quit CIDER

    ```
    M-x cider-quit RET
    ```

1. Reconnect to CIDER

    ```
    M-x cider-connect RET localhost RET RET
    ```

1. Observe no logs in the repl buffer going forward

1. *Note:* Using `M-x cider-log-show` doesn't produce different behavior AFAICT.
