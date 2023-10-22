@echo off
java	-cp	bin/	simulateur.Simulateur	-s	-mess	0100000000	-form	NRZ	-nbEch	100	-ti	100	0.5
java	-cp	bin/	simulateur.Simulateur	-s	-mess	00100000000000000000	-form	NRZ	-nbEch	100	-ti	200	0.5