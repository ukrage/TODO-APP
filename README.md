# TODO管理アプリ

タスクを管理するアプリです。
タスク名とタスク詳細を登録・変更・削除ができます。

## アプリケーションの概要図

後日掲載

## 開発環境

* IntelliJ IDEA
* 言語：Java17
* フレームワーク：Spring boot v3.2.2
* DB：H2 Database
* 環境：Windows11
* ソース管理：Git
* プロジェクト管理：GitHub

## API設計図

後日掲載

## DB定義

テーブル名：tasks

| カラム名     | データ型         | キー          | 備考      |
|----------|--------------|-------------|---------|
| id       | BIGINT       | PRIMARY KEY | ID,自動生成 |
| title    | VARCHAR(256) |             | タスク名    |
| content  | VARCHAR(256) |             | タスク詳細   |
| finished | BOOLEAN      |             | 終了判定    |
