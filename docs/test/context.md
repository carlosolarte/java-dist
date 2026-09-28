---
title: "Concurrency in Java — Preliminaries"
layout: slides
---

<style>
.slide { padding: 1.8rem 2.2rem; min-height: 560px; border-bottom: 1px solid #ddd; }
.slide h1 { font-size: 2.2rem; margin-bottom: .6rem; }
.slide h2 { font-size: 1.7rem; margin-bottom: .8rem; }
.slide .subtitle { font-size: 1.2rem; opacity: .78; }
.big { font-size: 1.35rem; }
.small { font-size: .92rem; opacity: .82; }
.timeline { display: grid; grid-template-columns: 120px 1fr; gap: .45rem .7rem; margin-top: 1rem; align-items: center; }
.label { font-weight: 700; }
.track { position: relative; height: 34px; background: #f4f4f4; border-radius: 8px; overflow: hidden; }
.block { position: absolute; top: 5px; height: 24px; border-radius: 6px; background: #4f7cff; color: white; text-align: center; line-height: 24px; font-size: .8rem; }
.block.alt { background: #28a37a; }
.block.warn { background: #d88923; }
.block.idle { background: repeating-linear-gradient(45deg,#ddd,#ddd 6px,#eee 6px,#eee 12px); color:#555; }
.row { display: flex; gap: 1rem; align-items: stretch; margin-top: 1rem; }
.card { flex: 1; border: 1px solid #ddd; border-radius: 14px; padding: 1rem; background: #fafafa; }
.worker { border: 2px solid #555; border-radius: 14px; padding: .75rem; text-align: center; font-weight: 700; background: white; }
.queue { display:flex; gap:.3rem; margin: .7rem 0; }
.task { padding:.45rem .65rem; border-radius: 7px; background:#e7ecff; border:1px solid #c6d0ff; font-weight:700; }
.center { text-align:center; }
.arrow { font-size: 2rem; line-height: 1; }
.question { font-size: 1.35rem; background:#fff8e6; border-left: 6px solid #e1a500; padding: .9rem 1rem; margin-top: 1rem; }
.two { display:grid; grid-template-columns: 1fr 1fr; gap: 1.2rem; align-items:start; }
pre.diagram { font-size: 1.05rem; background:#f7f7f7; padding:1rem; border-radius:12px; }
</style>

# Concurrency in Java

<div class="subtitle">Preliminaries: why threads exist before looking at Java syntax</div>

<div class="question">Key idea: several activities can make progress during the same period of time.</div>

---

<section class="slide">

## The problem: one worker, many tasks

<div class="big">Suppose we have three tasks:</div>

<div class="queue">
  <div class="task">A</div><div class="task">A</div><div class="task">A</div><div class="task">A</div>
  <div class="task">B</div><div class="task">B</div><div class="task">B</div>
  <div class="task">C</div><div class="task">C</div><div class="task">C</div><div class="task">C</div><div class="task">C</div>
</div>

<div class="row">
  <div class="card center">
    <div class="worker">Worker</div>
    <div class="arrow">↓</div>
    <pre class="diagram">AAAA → BBB → CCCCC</pre>
    <p><strong>Sequential execution</strong></p>
  </div>
  <div class="card">
    <p>The worker completes all of A, then all of B, then all of C.</p>
    <p class="small">This is simple and predictable, but it can be inefficient when tasks spend time waiting.</p>
  </div>
</div>

<!-- Speaker note: Ask students where this model appears in ordinary programs: read input, compute, print output. -->
</section>

---

<section class="slide">

## Concurrency without parallelism

<div class="big">A single worker can switch between tasks.</div>

<pre class="diagram">AA → B → C → A → BB → CC → A → CCC</pre>

<div class="timeline">
  <div class="label">Task A</div><div class="track"><div class="block" style="left:0%;width:18%">A</div><div class="block" style="left:36%;width:9%">A</div><div class="block" style="left:68%;width:12%">A</div></div>
  <div class="label">Task B</div><div class="track"><div class="block alt" style="left:22%;width:10%">B</div><div class="block alt" style="left:48%;width:16%">B</div></div>
  <div class="label">Task C</div><div class="track"><div class="block warn" style="left:34%;width:10%">C</div><div class="block warn" style="left:82%;width:16%">C</div></div>
</div>

<div class="question">Concurrency does not necessarily mean that two instructions execute at the exact same instant.</div>

</section>

---

<section class="slide">

## Parallelism: several workers

<div class="big">With several workers, tasks may truly run at the same time.</div>

<div class="timeline">
  <div class="label">Worker 1</div><div class="track"><div class="block" style="left:0%;width:33%">Task A</div></div>
  <div class="label">Worker 2</div><div class="track"><div class="block alt" style="left:0%;width:25%">Task B</div></div>
  <div class="label">Worker 3</div><div class="track"><div class="block warn" style="left:0%;width:42%">Task C</div></div>
</div>

<div class="two" style="margin-top:1.3rem">
  <div class="card">
    <h3>Concurrency</h3>
    <p>Several activities are in progress during overlapping time intervals.</p>
  </div>
  <div class="card">
    <h3>Parallelism</h3>
    <p>Several activities are executing simultaneously, usually on several cores.</p>
  </div>
</div>

</section>

---

<section class="slide">

## A timeline vocabulary

<div class="big">We will use lanes to represent threads and boxes to represent work.</div>

<div class="timeline">
  <div class="label">Thread A</div><div class="track"><div class="block" style="left:0%;width:22%">compute</div><div class="block idle" style="left:22%;width:27%">waiting</div><div class="block" style="left:49%;width:32%">compute</div></div>
  <div class="label">Thread B</div><div class="track"><div class="block alt" style="left:12%;width:30%">compute</div><div class="block idle" style="left:42%;width:25%">waiting</div><div class="block alt" style="left:67%;width:20%">compute</div></div>
  <div class="label">Thread C</div><div class="track"><div class="block idle" style="left:0%;width:38%">waiting</div><div class="block warn" style="left:38%;width:28%">compute</div></div>
</div>

<div class="row">
  <div class="card"><strong>Horizontal axis:</strong> time</div>
  <div class="card"><strong>One lane:</strong> one thread/activity</div>
  <div class="card"><strong>Empty/hatched parts:</strong> waiting, blocked, sleeping</div>
</div>

</section>

---

<section class="slide">

## Why threads? Waiting is everywhere

<div class="big">Many programs are not always computing.</div>

<div class="two">
  <div>
    <h3>Typical waiting situations</h3>
    <ul>
      <li>Waiting for a client request</li>
      <li>Waiting for a file or database</li>
      <li>Waiting for the network</li>
      <li>Waiting for user input</li>
    </ul>
  </div>
  <div class="card center">
    <p><strong>Bad use of one worker:</strong></p>
    <pre class="diagram">Client 1 blocks
Client 2 waits
Client 3 waits</pre>
  </div>
</div>

<div class="question">Question for students: should everyone wait because one task is blocked?</div>

</section>

---

<section class="slide">

## Example: a server with three clients

<div class="timeline">
  <div class="label">Client 1</div><div class="track"><div class="block" style="left:0%;width:16%">receive</div><div class="block idle" style="left:16%;width:55%">waiting for network</div><div class="block" style="left:71%;width:16%">reply</div></div>
  <div class="label">Client 2</div><div class="track"><div class="block alt" style="left:20%;width:28%">handle request</div><div class="block alt" style="left:50%;width:14%">reply</div></div>
  <div class="label">Client 3</div><div class="track"><div class="block warn" style="left:42%;width:25%">handle request</div><div class="block warn" style="left:69%;width:12%">reply</div></div>
</div>

<div class="row">
  <div class="card">
    <h3>Without concurrency</h3>
    <p>Client 2 and Client 3 may be forced to wait behind Client 1.</p>
  </div>
  <div class="card">
    <h3>With concurrency</h3>
    <p>While Client 1 is waiting for I/O, another thread can serve another client.</p>
  </div>
</div>

</section>

---

<section class="slide">

## Threads are not only about speed

<div class="big">A common misconception:</div>

<div class="question">“If I use threads, my program is automatically faster.”</div>

<div class="two">
  <div class="card">
    <h3>Threads help a lot when...</h3>
    <p>Some tasks spend time waiting: I/O, network, database, user interaction.</p>
  </div>
  <div class="card">
    <h3>Threads help only sometimes when...</h3>
    <p>All tasks are CPU-bound. Then actual parallelism and the number of cores matter.</p>
  </div>
</div>

<div class="small">Transition: Java gives us mechanisms to create and coordinate these activities.</div>

</section>

---

<section class="slide">

## From the picture to Java

<div class="timeline">
  <div class="label">Thread 1</div><div class="track"><div class="block" style="left:0%;width:30%">task</div><div class="block idle" style="left:30%;width:25%">waiting</div><div class="block" style="left:55%;width:25%">task</div></div>
  <div class="label">Thread 2</div><div class="track"><div class="block alt" style="left:18%;width:37%">task</div></div>
  <div class="label">Thread 3</div><div class="track"><div class="block warn" style="left:48%;width:30%">task</div></div>
</div>

<div class="row">
  <div class="card"><strong>Thread</strong><br/>An independent sequence of execution.</div>
  <div class="card"><strong>Scheduler</strong><br/>The system component deciding which thread runs when.</div>
  <div class="card"><strong>Interleaving</strong><br/>The observed order of small execution steps.</div>
</div>

<div class="question">Next step: how do we create these activities in Java?</div>

</section>
