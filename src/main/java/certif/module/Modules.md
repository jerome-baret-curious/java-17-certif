# Modules

```
[open] module le.mod.sandbox { // open pour export et opens tout package au runtime
        [export certif.jdbc;]*
        [export certif.flow to lautre.mod;]*
        requires java.base; // implicite
        [requires le.mod.requis;]*
        [requires transitive le.mod.plus.requis;]*
        [requires static le.mod.requis;]* // obligatoire compil, optionnel run
        [opens certif.stream;]* // permet l'accès runtime
        [opens certif.stream to encore.un.mod;]*
        [provides certif.module.StringGestion with certif.module.UnFournisseur,certif.module.UnAutreFournisseur;]*
        [uses le.pack.du.Service;]*
}
```
L'accès réflexif d'un module normal (non open) est possible sur le contenu `public` et `protected` des packages exportés.
Et sur tout le contenu des packages ouverts.

Noms des modules : identifiants Java séparés par des .  
Modules automatiques : byte-buddy-0.0.1.jar devient byte.buddy (avec version 0.0.1), mais `byte` est réservé donc `FindException`