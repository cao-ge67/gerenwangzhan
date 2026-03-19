-- 插入示例文章数据
USE blog_db;

-- 插入示例文章
INSERT INTO blog_article (title, content, summary, category_id, tags, view_count, like_count, comment_count, status, publish_time) VALUES
('Vue.js 3.0 新特性详解', 
'<h2>Vue.js 3.0 带来的重大变化</h2>
<p>Vue.js 3.0 是 Vue.js 框架的一个重要里程碑，它带来了许多令人兴奋的新特性和改进。</p>
<h3>1. Composition API</h3>
<p>Composition API 是 Vue 3.0 中最重要的新特性之一，它提供了一种全新的方式来组织和复用逻辑代码。</p>
<pre><code>import { ref, computed } from ''vue''</code></pre>
<h3>2. 性能提升</h3>
<p>Vue 3.0 在性能方面有了显著的提升，包括更小的包体积和更快的渲染速度。</p>
<h3>3. TypeScript 支持</h3>
<p>Vue 3.0 从头开始就是用 TypeScript 重写的，提供了更好的类型支持。</p>',
'Vue.js 3.0 带来了 Composition API、性能提升和更好的 TypeScript 支持，是前端开发的重要里程碑。',
1, 
'["Vue.js", "前端", "JavaScript"]',
1250, 
86, 
23, 
1, 
'2026-03-15 10:30:00'),

('Spring Boot 实战：构建RESTful API',
'<h2>使用 Spring Boot 构建 RESTful API</h2>
<p>Spring Boot 是目前最流行的 Java 后端框架之一，它让开发者能够快速构建生产级别的应用程序。</p>
<h3>1. 项目初始化</h3>
<p>使用 Spring Initializr 可以快速创建 Spring Boot 项目，选择需要的依赖即可。</p>
<h3>2. 创建 REST Controller</h3>
<p>通过 @RestController 注解可以轻松创建 RESTful API 端点。</p>
<pre><code>@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}</code></pre>
<h3>3. 数据库集成</h3>
<p>Spring Boot 提供了多种数据库集成方案，包括 JPA、MyBatis 等。</p>',
'Spring Boot 提供了快速构建 RESTful API 的能力，本文介绍从项目初始化到数据库集成的完整流程。',
1,
'["Spring Boot", "Java", "后端开发"]',
2340,
156,
45,
1,
'2026-03-14 14:20:00'),

('深入理解 JavaScript 闭包',
'<h2>JavaScript 闭包详解</h2>
<p>闭包是 JavaScript 中一个重要且强大的概念，理解它对于编写高质量的 JavaScript 代码至关重要。</p>
<h3>什么是闭包？</h3>
<p>闭包是指有权访问另一个函数作用域中变量的函数。创建闭包的常见方式就是在一个函数内部创建另一个函数。</p>
<pre><code>function outerFunction() {
    let outerVariable = ''I am outside!'';
    
    function innerFunction() {
        console.log(outerVariable);
    }
    
    return innerFunction;
}

const myClosure = outerFunction();
myClosure(); // 输出: I am outside!</code></pre>
<h3>闭包的应用场景</h3>
<ul>
<li>数据私有化</li>
<li>函数工厂</li>
<li>模块模式</li>
</ul>',
'闭包是 JavaScript 的核心概念之一，本文详细讲解闭包的原理、应用场景和注意事项。',
2,
'["JavaScript", "前端", "编程基础"]',
3120,
203,
67,
1,
'2026-03-13 09:15:00'),

('CSS Grid 布局完全指南',
'<h2>CSS Grid 布局完全指南</h2>
<p>CSS Grid 是一个强大的二维布局系统，它彻底改变了我们构建网页布局的方式。</p>
<h3>1. 基本概念</h3>
<p>Grid 布局由容器和项目组成，通过定义行和列来创建网格结构。</p>
<pre><code>.container {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-template-rows: auto;
    gap: 20px;
}</code></pre>
<h3>2. 常用属性</h3>
<ul>
<li>grid-template-columns: 定义列</li>
<li>grid-template-rows: 定义行</li>
<li>gap: 设置间距</li>
<li>grid-area: 定义区域</li>
</ul>
<h3>3. 实战案例</h3>
<p>通过 Grid 可以轻松实现复杂的响应式布局，如卡片网格、仪表盘等。</p>',
'CSS Grid 提供了强大的二维布局能力，本文从基础概念到实战案例全面介绍 Grid 布局。',
1,
'["CSS", "前端", "布局"]',
1876,
124,
38,
1,
'2026-03-12 16:45:00'),

('Docker 容器化部署实践',
'<h2>Docker 容器化部署实践</h2>
<p>Docker 已经成为现代应用部署的标准工具，它能够确保应用在任何环境中都能一致运行。</p>
<h3>1. Docker 基础概念</h3>
<p>容器是一种轻量级的虚拟化技术，它共享主机内核但拥有独立的文件系统。</p>
<h3>2. 编写 Dockerfile</h3>
<pre><code>FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/myapp.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]</code></pre>
<h3>3. Docker Compose</h3>
<p>Docker Compose 可以定义和运行多容器 Docker 应用程序，特别适合微服务架构。</p>',
'Docker 容器化技术能够简化部署流程，本文介绍 Docker 的基础概念和实际应用。',
2,
'["Docker", "DevOps", "部署"]',
2654,
178,
52,
1,
'2026-03-11 11:30:00'),

('React Hooks 最佳实践',
'<h2>React Hooks 最佳实践</h2>
<p>React Hooks 改变了我们编写 React 组件的方式，让函数组件也能拥有状态和生命周期。</p>
<h3>1. 常用 Hooks</h3>
<ul>
<li>useState: 管理组件状态</li>
<li>useEffect: 处理副作用</li>
<li>useContext: 访问上下文</li>
<li>useMemo: 记忆化计算</li>
</ul>
<h3>2. 自定义 Hooks</h3>
<p>自定义 Hooks 是复用逻辑的强大工具，可以将组件逻辑提取到可重用的函数中。</p>
<pre><code>function useWindowSize() {
    const [size, setSize] = useState({
        width: window.innerWidth,
        height: window.innerHeight
    });
    
    useEffect(() => {
        const handleResize = () => {
            setSize({
                width: window.innerWidth,
                height: window.innerHeight
            });
        };
        
        window.addEventListener(''resize'', handleResize);
        return () => window.removeEventListener(''resize'', handleResize);
    }, []);
    
    return size;
}</code></pre>',
'React Hooks 让函数组件更加强大，本文介绍常用 Hooks 的使用方法和自定义 Hooks 的最佳实践。',
1,
'["React", "前端", "JavaScript"]',
2987,
215,
71,
1,
'2026-03-10 13:20:00'),

('MySQL 性能优化技巧',
'<h2>MySQL 性能优化技巧</h2>
<p>数据库性能优化是后端开发中的重要课题，良好的优化可以显著提升应用响应速度。</p>
<h3>1. 索引优化</h3>
<p>合理的索引设计是提升查询性能的关键，需要根据查询模式创建合适的索引。</p>
<h3>2. 查询优化</h3>
<ul>
<li>避免 SELECT *</li>
<li>使用 LIMIT 限制结果集</li>
<li>优化 JOIN 操作</li>
<li>使用 EXPLAIN 分析查询</li>
</ul>
<h3>3. 配置优化</h3>
<p>根据服务器硬件和应用特点调整 MySQL 配置参数，如 buffer_pool_size、max_connections 等。</p>',
'MySQL 性能优化涉及索引、查询和配置多个方面，本文总结实用的优化技巧和最佳实践。',
2,
'["MySQL", "数据库", "性能优化"]',
3421,
267,
89,
1,
'2026-03-09 15:10:00'),

('前端工程化：从零搭建项目',
'<h2>前端工程化：从零搭建项目</h2>
<p>现代前端开发已经从简单的 HTML/CSS/JavaScript 发展到复杂的工程化体系。</p>
<h3>1. 项目结构设计</h3>
<p>良好的项目结构是可维护性的基础，需要考虑模块划分、代码组织等因素。</p>
<h3>2. 构建工具选择</h3>
<ul>
<li>Webpack: 功能强大但配置复杂</li>
<li>Vite: 快速且易用</li>
<li>Rollup: 适合库开发</li>
</ul>
<h3>3. 代码规范</h3>
<p>使用 ESLint、Prettier 等工具确保代码质量和风格一致性。</p>',
'前端工程化是现代开发的基础，本文介绍项目结构、构建工具和代码规范的实践经验。',
3,
'["前端工程化", "构建工具", "代码规范"]',
2234,
189,
54,
1,
'2026-03-08 10:45:00'),

('TypeScript 高级类型应用',
'<h2>TypeScript 高级类型应用</h2>
<p>TypeScript 的类型系统非常强大，掌握高级类型可以写出更安全、更优雅的代码。</p>
<h3>1. 泛型</h3>
<p>泛型是 TypeScript 的核心特性之一，它允许我们编写可重用的组件和函数。</p>
<pre><code>function identity<T>(arg: T): T {
    return arg;
}</code></pre>
<h3>2. 条件类型</h3>
<p>条件类型可以根据类型关系选择不同的类型，实现类型级别的逻辑判断。</p>
<h3>3. 映射类型</h3>
<p>映射类型可以基于现有类型创建新类型，如 Partial、Required 等工具类型。</p>',
'TypeScript 高级类型能够提升代码质量和开发体验，本文深入讲解泛型、条件类型和映射类型。',
1,
'["TypeScript", "前端", "类型系统"]',
2765,
234,
63,
1,
'2026-03-07 14:30:00'),

('微服务架构设计原则',
'<h2>微服务架构设计原则</h2>
<p>微服务架构已经成为大型应用的主流架构模式，但正确实施微服务需要遵循一定的原则。</p>
<h3>1. 单一职责</h3>
<p>每个微服务应该专注于单一业务领域，避免服务过于庞大和复杂。</p>
<h3>2. 服务自治</h3>
<p>微服务应该能够独立部署、扩展和演进，不依赖于其他服务。</p>
<h3>3. 接口设计</h3>
<p>良好的 API 设计是微服务通信的基础，需要考虑版本控制、错误处理等因素。</p>
<h3>4. 数据管理</h3>
<p>每个微服务应该拥有自己的数据库，避免跨服务的数据访问。</p>',
'微服务架构设计需要遵循单一职责、服务自治等原则，本文介绍微服务架构的核心设计理念。',
2,
'["微服务", "架构设计", "后端开发"]',
3892,
312,
98,
1,
'2026-03-06 09:20:00');