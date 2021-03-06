module.exports = {
  base: '/redis/',
  title: 'Redis + Spring Start',
  description: 'Redis 설치 방법부터 Spring boot 연동 방법, Redis 기능 사용까지!!',
  head: [
    ['link', { rel: "icon", href: "/favicon.png"}]
  ],
  theme: '@vuepress/blog',
  themeConfig: {
    navbar: true,
    nav: [
      { text: 'Home', link: '/' }
    ],
    sidebar: [
      {
        title: 'Getting Started',
        path: '/install',
        collapsable: false,
        sidebarDepth: 1,
        children: [
          '/install/linux',
          '/install/spring'
        ]
      },
      {
        title: 'Redis',
        path: '/redis',
        collapsable: false,
        sidebarDepth: 1,
        children: [
          '/redis/port',
          '/redis/auth',
          '/redis/cluster',
          '/redis/replica'
        ]
      }
    ]
  },
  frontMatter: {
    foo: 'bar'
  },
  configureWebpack: {
    resolve: {
      alias: {
        // '@': '/'
      }
    }
  },
  markdown: {
    lineNumbers: true
  },
  plugins: [
    "@vuepress/back-to-top",
    [
      "@vuepress/google-analytics"
    ],
    "@vuepress/nprogress"
  ]
}
