#!/usr/bin/env sh

# abort on errors
set -e

# build
vuepress build docs

# navigate into the build output directory
cd docs/.vuepress/dist

# if you are deploying to a custom domain
# echo 'www.example.com' > CNAME

git init
git add -A
git commit -m 'deploy'

git config --local user.name iinow
git config --local user.email iinow@naver.com

# if you are deploying to https://<USERNAME>.github.io
# git push -f git@github.com:<USERNAME>/<USERNAME>.github.io.git master

# if you are deploying to https://<USERNAME>.github.io/<REPO>
# git@github.com:iinow/redis.git
git push -f https://iinow:qweqweqwe2!@github.com/iinow/redis.git master:gh-pages

cd -
