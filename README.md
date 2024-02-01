# TODO管理アプリ

タスクを管理するAPIです。
タスク名とタスク詳細を登録・変更・削除ができます。

## アプリケーション

アーキテクチャ図
![アプリケーション概要図.png](..%2F..%2FUsers%2Fcfjmv%2FOneDrive%2FPictures%2F%E3%82%A2%E3%83%97%E3%83%AA%E3%82%B1%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3%E6%A6%82%E8%A6%81%E5%9B%B3.png)

## 開発環境

* IntelliJ IDEA
* 言語：Java17
* フレームワーク：Spring boot v3.2.2
* DB：H2 Database
* 環境：Windows11
* ソース管理：Git
* プロジェクト管理：GitHub

## API仕様書

https://ukrage.github.io/todo-app/swagger/

## DB定義

テーブル名：tasks

| カラム名     | データ型         | キー          | 備考      |
|----------|--------------|-------------|---------|
| id       | BIGINT       | PRIMARY KEY | ID,自動生成 |
| title    | VARCHAR(256) |             | タスク名    |
| content  | VARCHAR(256) |             | タスク詳細   |
| finished | BOOLEAN      |             | 終了判定    |
