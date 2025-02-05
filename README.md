## Repo for Hextree Intent Attack using Kotlin

- https://app.hextree.io/courses/intent-threat-surface/

## Flag 13

- change the search url to `hex://flag?action=give-me` (need to use chrome)
- i create a flag 13 button that could work as intented

## Flag 14

- use burp to intercept and replace the response `type=user` to `type=admin`

## Flag 15

- use chrome and search for `intent:#Intent;package=io.hextree.attacksurface;action=io.hextree.action.GIVE_FLAG;S.action=flag;B.flag=true;end`
- must chrome cause its a chrome feature
