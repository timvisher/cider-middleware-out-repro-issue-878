# CIDER '`middleware.out` may stop working after a reconnect?' Repro

[cidre-nrepl/Issue 878](https://github.com/clojure-emacs/cider-nrepl/issues/878)

Note this may have something to do with incomplete Log4j support noted [here in the manual](https://docs.cider.mx/cider/debugging/logging.html#log-framework). That links to [clojure-emacs/logjam issue #2](https://github.com/clojure-emacs/logjam/issues/2) which talks about dynamic reconfiguration of the appender being wiped out somehow by log4j2's reconfiguration mechanism. So this could be a known issue.

1. Run CIDER (`bin/start-cider`)

    ```
    $ bin/start-cider
    2024-05-22T11:51:25+0000
    Clojure CLI version 1.11.3.1463
    nREPL server started on port 56389 on host 0.0.0.0 - nrepl://0.0.0.0:56389
    ```

1. Connect from Emacs

    ```
    $ emacs cider-middleware-out-repro-issue-878.clj
    ```

    ```
    (progn
      (cider-version)
      (message "%s" (emacs-version))
      nil)
    ```

    ```
    CIDER 1.13.1 (Santiago)
    GNU Emacs 29.3 (build 1, aarch64-apple-darwin23.4.0)
     of 2024-03-24
    ```

    ```
    M-x cider-connect RET localhost RET RET
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
