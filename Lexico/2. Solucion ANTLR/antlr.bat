cls
java -jar "antlr\antlr-4.7.1-complete.jar" -no-listener -package parser src\parser\Lexicon.g4

@echo off
del src\parser\*.tokens
del src\parser\*.interp
