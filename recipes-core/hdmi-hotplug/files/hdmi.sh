#!/bin/sh

export XAUTHORITY=/home/root/.Xauthority
export DISPLAY=:0.0
DISPLAY=:0.0 xrandr --output HDMI-0 --auto --primary --output LVDS-0 --auto --right-of HDMI-0
lxpanelctl restart
