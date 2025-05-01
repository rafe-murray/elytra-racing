# OG Elytra Boosters
The goal of this mod is to add features for use in elytra maps.

## Planned Features

- Horizontal/Vertical boosters
  - Probably create a block that acts like an air block
  - Adds velocity in the booster direction
  - Use blockstate to track which faces are connected to another booster
  - Hide those faces
- Rings
  - Lower priority
  - Another transparent block?
  - Should be like barrier blocks while placing
  - Need way to register different contiguous regions of ring blocks as part of the same "course"
  - I.e., all rings must be passed through in order to complete the course
- Checkpoints
  - Walking or dying while in a course should reset the player's position to the latest checkpoint block in the course
  - Need a way to register checkpoints within a course
  - Must track the most recently visited
  - Also need a way to start/exit a course
- Crafting recipes for Survival
  - Low priority